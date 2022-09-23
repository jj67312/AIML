package ai_ml.experiment3;

import java.util.*;

class Node {
    int mat[][] = new int[3][3];
    int gVal;
    int hVal;
    int fVal;
    int rEmpty, lEmpty;
    Node parentNode;

    Node(int mat[][], int hVal, Node parentNode) {
        this.mat = mat;
        this.hVal = hVal;
        if (parentNode == null) {
            this.gVal = 0;
        } else {
            this.gVal = parentNode.gVal + 1;
        }
        this.fVal = this.gVal + this.hVal;
        // set coordinates of empty cell:
        int loc[] = findEmpty(this.mat);
        this.rEmpty = loc[0];
        this.lEmpty = loc[1];
    }

    int[] findEmpty(int mat[][]) {
        int loc[] = new int[2];
        int r = -1, l = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mat[i][j] == 0) {
                    r = i;
                    l = j;
                    break;
                }
            }
        }
        loc[0] = r;
        loc[1] = l;
        return loc;
    }
}

class Checker implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        if (n1.fVal > n2.fVal) return 1;
        else return -1;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = 3;
        int mat[][] = new int[3][3];
        int finalMat[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = sc.nextInt();
            }
        }

        Node srcNode = new Node(mat, calHVal(mat), null);
        printNode(srcNode);

        PriorityQueue<Node> open = new PriorityQueue<>(new Checker());
        Queue<Node> closed = new LinkedList<>();

        // have srcNode
        // generate all possible child nodes and enter them in open,
        // remove the srcNode from the closed
        // remove the element from the open and make it srcNode and insert into closed
        // repeat the steps till srcNode == is target matrix

        while (!isSolved(srcNode.mat)) {

            closed.add(srcNode);
            // shift empty to up:
            if (srcNode.rEmpty != 0) {
                String shiftPos = "up";
                Node childNode = generateNewNode(srcNode, shiftPos, getCopy(srcNode.mat));
                open.add(childNode);
                // if we reach the target element just break the loop
                printNode(childNode);
                if(isSolved(childNode.mat)) {
                    System.out.println("SOLVED");
                    break;
                }
            }

            // shift empty to down:
            if (srcNode.rEmpty != n - 1) {
                String shiftPos = "down";
                Node childNode = generateNewNode(srcNode, shiftPos, getCopy(srcNode.mat));
                open.add(childNode);
                printNode(childNode);

                // if we reach the target element just break the loop
                if(isSolved(childNode.mat)) {
                    System.out.println("SOLVED");
                    break;
                }
            }

            // shift empty to left:
            if (srcNode.lEmpty != 0) {
                String shiftPos = "left";
                Node childNode = generateNewNode(srcNode, shiftPos, getCopy(srcNode.mat));
                open.add(childNode);
                printNode(childNode);

                // if we reach the target element just break the loop
                if(isSolved(childNode.mat)) {
                    System.out.println("Solved");
                    break;
                }
            }

            // shift empty to down:
            if (srcNode.lEmpty != n - 1) {
                String shiftPos = "right";
                Node childNode = generateNewNode(srcNode, shiftPos, getCopy(srcNode.mat));
                open.add(childNode);
                printNode(childNode);

                // if we reach the target element just break the loop
                if(isSolved(childNode.mat)) {
                    System.out.println("Solved");
                    break;
                }
            }

            closed.poll();
            srcNode = open.poll();
        }


        sc.close();
    }

    static Node generateNewNode(Node parentNode, String shiftPos, int parentMat[][]) {
        int row = -1, col = -1;

        if (shiftPos == "up") {
            row = parentNode.rEmpty - 1;
            col = parentNode.lEmpty;
        } else if (shiftPos == "down") {
            row = parentNode.rEmpty + 1;
            col = parentNode.lEmpty;
        } else if (shiftPos == "left") {
            row = parentNode.rEmpty;
            col = parentNode.lEmpty - 1;
        } else if (shiftPos == "right") {
            row = parentNode.rEmpty;
            col = parentNode.lEmpty + 1;
        }

        int temp = parentMat[row][col];
        parentMat[row][col] = parentMat[parentNode.rEmpty][parentNode.lEmpty];
        parentMat[parentNode.rEmpty][parentNode.lEmpty] = temp;

        Node newNode = new Node(parentMat, calHVal(parentMat), parentNode);
        return newNode;
    }

    static int calHVal(int mat[][]) {
        int finalMat[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int hVal = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mat[i][j] != finalMat[i][j] && mat[i][j] != 0) {
                    hVal++;
                }
            }
        }
        return hVal;
    }

    static boolean isSolved(int mat[][]) {
        boolean ans = false;
        int finalMat[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mat[i][j] == finalMat[i][j]) {
                    ans = true;
                } else {
                    ans = false;
                    break;
                }
            }
        }
        return ans;
    }

    static void printNode(Node node) {
        System.out.println("Matrix:");
        printMat(node.mat);
        System.out.println("H value = " + node.hVal);
        System.out.println("G value = " + node.gVal);
        System.out.println("F value = " + node.fVal);
        System.out.println("--------------------------------------------------------");
    }

    static void printMat(int mat[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int[][] getCopy(int mat[][]) {
        int newMat[][] = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newMat[i][j] = mat[i][j];
            }
        }
        return newMat;
    }
}
