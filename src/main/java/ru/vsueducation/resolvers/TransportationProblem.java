package ru.vsueducation.resolvers;
import com.google.gson.Gson;
import ru.vsueducation.utils.GetJsonResult;
import ru.vsueducation.utils.ResponseObject;

import java.io.File;
import java.util.*;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;

public class TransportationProblem implements GetJsonResult {

    private int[] demand;
    private int[] supply;
    private double[][] costs;
    private Shipment[][] matrix;

    private class Shipment {
        final double costPerUnit;
        final int r, c;
        double quantity;

        public Shipment(double q, double cpu, int r, int c) {
            quantity = q;
            costPerUnit = cpu;
            this.r = r;
            this.c = c;
        }
    }

    public TransportationProblem(final ru.vsueducation.server.routes.TransportationProblem.Body body) {
        this.init(body);
        this.northWestCornerRule();
        this.steppingStone();
    }

    public TransportationProblem() {}

    void init(final ru.vsueducation.server.routes.TransportationProblem.Body body) {
        int numSources = body.x;
        int numDestinations = body.y;
        List<Integer> src = body.posts;
        List<Integer> dst = body.shops;
        int totalSrc = src.stream().mapToInt(i -> i).sum();
        int totalDst = dst.stream().mapToInt(i -> i).sum();
        if (totalSrc > totalDst)
            dst.add(totalSrc - totalDst);
        else if (totalDst > totalSrc)
            src.add(totalDst - totalSrc);

        supply = src.stream().mapToInt(i -> i).toArray();
        demand = dst.stream().mapToInt(i -> i).toArray();

        costs = new double[supply.length][demand.length];
        matrix = new Shipment[supply.length][demand.length];

        for (int i = 0; i < numSources; i++)
            for (int j = 0; j < numDestinations; j++)
                costs[i][j] = body.table.get(i).get(j);

    }

    void init(String filename) throws Exception {

        try (Scanner sc = new Scanner(new File(filename))) {
            int numSources = sc.nextInt();
            int numDestinations = sc.nextInt();

            List<Integer> src = new ArrayList<>();
            List<Integer> dst = new ArrayList<>();

            for (int i = 0; i < numSources; i++)
                src.add(sc.nextInt());

            for (int i = 0; i < numDestinations; i++)
                dst.add(sc.nextInt());

            // fix imbalance
            int totalSrc = src.stream().mapToInt(i -> i).sum();
            int totalDst = dst.stream().mapToInt(i -> i).sum();
            if (totalSrc > totalDst)
                dst.add(totalSrc - totalDst);
            else if (totalDst > totalSrc)
                src.add(totalDst - totalSrc);

            supply = src.stream().mapToInt(i -> i).toArray();
            demand = dst.stream().mapToInt(i -> i).toArray();

            costs = new double[supply.length][demand.length];
            matrix = new Shipment[supply.length][demand.length];

            for (int i = 0; i < numSources; i++)
                for (int j = 0; j < numDestinations; j++)
                    costs[i][j] = sc.nextDouble();
        }
    }

    void northWestCornerRule() {

        for (int r = 0, northwest = 0; r < supply.length; r++)
            for (int c = northwest; c < demand.length; c++) {

                int quantity = Math.min(supply[r], demand[c]);
                if (quantity > 0) {
                    matrix[r][c] = new Shipment(quantity, costs[r][c], r, c);

                    supply[r] -= quantity;
                    demand[c] -= quantity;

                    if (supply[r] == 0) {
                        northwest = c;
                        break;
                    }
                }
            }
    }

    void steppingStone() {
        double maxReduction = 0;
        Shipment[] move = null;
        Shipment leaving = null;

        fixDegenerateCase();

        for (int r = 0; r < supply.length; r++) {
            for (int c = 0; c < demand.length; c++) {

                if (matrix[r][c] != null)
                    continue;

                Shipment trial = new Shipment(0, costs[r][c], r, c);
                Shipment[] path = getClosedPath(trial);

                double reduction = 0;
                double lowestQuantity = Integer.MAX_VALUE;
                Shipment leavingCandidate = null;

                boolean plus = true;
                for (Shipment s : path) {
                    if (plus) {
                        reduction += s.costPerUnit;
                    } else {
                        reduction -= s.costPerUnit;
                        if (s.quantity < lowestQuantity) {
                            leavingCandidate = s;
                            lowestQuantity = s.quantity;
                        }
                    }
                    plus = !plus;
                }
                if (reduction < maxReduction) {
                    move = path;
                    leaving = leavingCandidate;
                    maxReduction = reduction;
                }
            }
        }

        if (move != null) {
            double q = leaving.quantity;
            boolean plus = true;
            for (Shipment s : move) {
                s.quantity += plus ? q : -q;
                matrix[s.r][s.c] = s.quantity == 0 ? null : s;
                plus = !plus;
            }
            steppingStone();
        }
    }

    LinkedList<Shipment> matrixToList() {
        return stream(matrix)
                .flatMap(row -> stream(row))
                .filter(s -> s != null)
                .collect(toCollection(LinkedList::new));
    }

    Shipment[] getClosedPath(Shipment s) {
        LinkedList<Shipment> path = matrixToList();
        path.addFirst(s);

        // remove (and keep removing) elements that do not have a
        // vertical AND horizontal neighbor
        while (path.removeIf(e -> {
            Shipment[] nbrs = getNeighbors(e, path);
            return nbrs[0] == null || nbrs[1] == null;
        }));

        // place the remaining elements in the correct plus-minus order
        Shipment[] stones = path.toArray(new Shipment[path.size()]);
        Shipment prev = s;
        for (int i = 0; i < stones.length; i++) {
            stones[i] = prev;
            prev = getNeighbors(prev, path)[i % 2];
        }
        return stones;
    }

    Shipment[] getNeighbors(Shipment s, LinkedList<Shipment> lst) {
        Shipment[] nbrs = new Shipment[2];
        for (Shipment o : lst) {
            if (o != s) {
                if (o.r == s.r && nbrs[0] == null)
                    nbrs[0] = o;
                else if (o.c == s.c && nbrs[1] == null)
                    nbrs[1] = o;
                if (nbrs[0] != null && nbrs[1] != null)
                    break;
            }
        }
        return nbrs;
    }

    void fixDegenerateCase() {
        final double eps = Double.MIN_VALUE;

        if (supply.length + demand.length - 1 != matrixToList().size()) {

            for (int r = 0; r < supply.length; r++)
                for (int c = 0; c < demand.length; c++) {
                    if (matrix[r][c] == null) {
                        Shipment dummy = new Shipment(eps, costs[r][c], r, c);
                        if (getClosedPath(dummy).length == 0) {
                            matrix[r][c] = dummy;
                            return;
                        }
                    }
                }
        }
    }

    @Override
    public String getJsonResult() {
        List<ResponseObject<String>> list = new ArrayList<>();
        final String[][] stringMatrix = new String[supply.length][demand.length];
        double totalCosts = 0;

        for (int r = 0; r < supply.length; r++) {
            for (int c = 0; c < demand.length; c++) {

                Shipment s = matrix[r][c];
                if (s != null && s.r == r && s.c == c) {
                    stringMatrix[r][c] = String.valueOf((double) Math.round(s.quantity * 100) / 100);
                    totalCosts += (s.quantity * s.costPerUnit);
                } else
                    stringMatrix[r][c] = "-";
            }
        }
        final double totalCostsFinal = totalCosts;
        ResponseObject<String> responseObject = new ResponseObject<>();
        responseObject.setText("Оптимальное решение");
        responseObject.setTable(stringMatrix);
        list.add(responseObject);
        responseObject = new ResponseObject<>();
        responseObject.setText("Затраты: " + totalCostsFinal);
        list.add(responseObject);
        final Gson gson = new Gson();
        final String test = gson.toJson(list);
        return test;
    }
}