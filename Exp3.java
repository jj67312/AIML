//package ai_ml;
//
//
//import java.util.*;
//
//class Index {
//    int r, l;
//
//    Index(int r, int l) {
//        this.r = r;
//        this.l = l;
//    }
//}
//
//public class Node {
//    int hVal;
//    int mat[][] = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
//    Node parentNode;
//    int gVal;
//    public int fVal;
//    Index emptyIndex;
//
//    Node(int hVal, int mat[][], Node parentNode, Index emptyIndex) {
//        this.hVal = hVal;
//        if (parentNode == null) {
//            this.gVal = 0;
//        } else {
//            this.gVal = parentNode.gVal + 1;
//        }
//        this.mat = mat;
//        this.emptyIndex = emptyIndex;
//        this.fVal = this.gVal + this.hVal;
//    }
//}
//
//class Checker implements Comparator<Node> {
//    public int compare(Node n1, Node n2) {
//        if (n1.fVal > n2.fVal) return 1;
//        else return -1;
//    }
//}
//
//public class Exp3 {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        int n = 3;
//        int matrix[][] = {{2, 1, 3}, {4, 6, 5}, {7, 8, 0}};
//        int finalMatrix[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
//
//
//        PriorityQueue<Node> pQueue = new PriorityQueue<>(new Checker());
//
//        Index emptyIndex = findEmptyIndex(matrix);
//        System.out.println("Empty cell coordinates = " + emptyIndex.r + " " + emptyIndex.l);
//        Node srcNode = new Node(calMisplaced(matrix, finalMatrix), matrix, null, emptyIndex);
//
//        // Add src node to priority queue:
//        pQueue.add(srcNode);
//
//        boolean solved = false;
//
//        while (!pQueue.isEmpty() && !solved) {
//
//            // condition for shifting up:
//            String shiftPos = "";
//            int newMat[][] = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
//            if (emptyIndex.r != 0) {
//                shiftPos = "up";
//                newMat = getCopy(matrix);
//                printMatrix(newMat);
//                System.out.println();
//
//                if(!isSolved(newMat, finalMatrix))  {
//                    pQueue.add(generateNew(newMat, shiftPos, emptyIndex, finalMatrix, null));
//                } else {
//                    solved = true;
//                    break;
//                }
//            }
//
//            // condition for shifting down:
//            if (emptyIndex.r != n - 1) {
//                shiftPos = "down";
//                newMat = getCopy(matrix);
//                printMatrix(newMat);
//                System.out.println();
//
//                if(!isSolved(newMat, finalMatrix)) {
//                    pQueue.add(generateNew(newMat, shiftPos, emptyIndex, finalMatrix, null));
//                } else {
//                    solved = true;
//                    break;
//                }
//            }
//
//            // condition for shifting left:
//            if (emptyIndex.l != 0) {
//                shiftPos = "left";
//                newMat = getCopy(matrix);
//                printMatrix(newMat);
//                System.out.println();
//
//                if(!isSolved(newMat, finalMatrix)) {
//                    pQueue.add(generateNew(newMat, shiftPos, emptyIndex, finalMatrix, null));
//
//                } else {
//                    solved = true;
//                    break;
//                }
//            }
//
//
//            // condition for shifting right:
//            if (emptyIndex.l != n - 1) {
//                shiftPos = "right";
//                newMat = getCopy(matrix);
//                printMatrix(newMat);
//                System.out.println();
//                if(!isSolved(newMat, finalMatrix)) {
//                    pQueue.add(generateNew(newMat, shiftPos, emptyIndex, finalMatrix, null));
//                } else {
//                    solved = true;
//                    break;
//                }
//            }
//
//            matrix = pQueue.poll().mat;
//        }
//
//        if(solved) {
//            System.out.println("Solved");
//            printMatrix(matrix);
//        }
//
//        sc.close();
//    }
//
//    static Index findEmptyIndex(int mat[][]) {
//        int i = 0, j = 0;
//        Index emptyIndex = new Index(-1, -1);
//        for (i = 0; i < 3; i++) {
//            for (j = 0; j < 3; j++) {
//                if (mat[i][j] == 0) {
//                    emptyIndex.r = i;
//                    emptyIndex.l = j;
//                    break;
//                }
//            }
//        }
//        return emptyIndex;
//    }
//
//    static void printMatrix(int mat[][]) {
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(mat[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
//
//    static boolean isSolved(int mat[][], int finalMat[][]) {
//        if (Arrays.equals(mat, finalMat)) {
//            return true;
//        }
//        return false;
//    }
//
//    static int[][] getCopy(int mat[][]) {
//        int newMat[][] = new int[3][3];
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                newMat[i][j] = mat[i][j];
//            }
//        }
//        return newMat;
//    }
//
//    static int calMisplaced(int mat[][], int finalMat[][]) {
//        int total = 0;
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (mat[i][j] != finalMat[i][j] && mat[i][j] != 0) {
//                    total++;
//                }
//            }
//        }
//        return total;
//    }
//
//    static Node generateNew(int mat[][], String shiftPos, Index emptyIndex, int finalMatrix[][], Node parentNode) {
//        int row = -1, col = -1;
//
//        if (shiftPos == "up") {
//            row = emptyIndex.r - 1;
//            col = emptyIndex.l;
//        } else if (shiftPos == "down") {
//            row = emptyIndex.r + 1;
//            col = emptyIndex.l;
//        } else if (shiftPos == "left") {
//            row = emptyIndex.r;
//            col = emptyIndex.l - 1;
//        } else if (shiftPos == "right") {
//            row = emptyIndex.r;
//            col = emptyIndex.l + 1;
//
//        }
//
//        int temp = mat[row][col];
//        mat[row][col] = mat[emptyIndex.r][emptyIndex.l];
//        mat[emptyIndex.r][emptyIndex.l] = temp;
//
//
//        Index newEmptyIndex = findEmptyIndex(mat);
//        Node newNode = new Node(calMisplaced(mat, finalMatrix), mat, parentNode, newEmptyIndex);
//        return newNode;
//    }
//}
