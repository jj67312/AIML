package exp3;

import java.util.*;

class Node {
	int mat[][] = new int[3][3];
	int gVal;
	int hVal;
	int fVal;
	int x, y;
	int parentX, parentY;
	Node parentNode;

	Node(int mat[][], int hVal, Node parentNode, int parentX, int parentY) {
		this.mat = mat;
		this.hVal = hVal;
		this.parentNode = parentNode;
		if (parentNode == null) {
			this.gVal = 0;
		} else {
			this.gVal = parentNode.gVal + 1;
		}
		this.fVal = this.gVal + this.hVal;
		// set coordinates of empty cell:
		int loc[] = findEmpty(this.mat);
		this.x = loc[0];
		this.y = loc[1];
		this.parentX = parentX;
		this.parentY = parentY;
	}

	int[] findEmpty(int mat[][]) {
		int loc[] = new int[2];
		int r = -1, c = -1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (mat[i][j] == 0) {
					r = i;
					c = j;
					break;
				}
			}
		}
		loc[0] = r;
		loc[1] = c;
		return loc;
	}
}

class Checker implements Comparator<Node> {
	public int compare(Node n1, Node n2) {
		return n1.fVal - n2.fVal;
	}
}

public class astar {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = 3;
		int mat[][] = new int[n][n];

		boolean solvable = false;
		int[] inversionArray = new int[n * n]; // flattened out array to check no of inversions
		int k = 0;

		System.out.println("Enter the matrix to be solved:\n");
		for (int i = 0; i < n; i++) {
			System.out.println("For row " + (i + 1));
			for (int j = 0; j < n; j++) {
				System.out.print("Enter element " + (j + 1) + " :");
				mat[i][j] = sc.nextInt();
				inversionArray[k] = mat[i][j];
				k++;
			}
		}
		int inversions = 0;
		for (int i = 0; i < n * n - 1; i++) {
			for (int j = i + 1; j < n * n; j++) {
				if (inversionArray[i] > inversionArray[j] && inversionArray[i] != 0 && inversionArray[j] != 0) {
					inversions++;
				}
			}
		}

		System.out.println("No of inversions = " + inversions);

		if (inversions % 2 == 0) {
			System.out.println("Sum is solvable");
			solvable = true;
		} else {
			System.out.println("Sum is not solvable");
			solvable = false;
		}

		if (solvable) {
			solve(mat, n);
		} else {
			System.out.println("Unsolvable");
			System.exit(0);
		}

		sc.close();
	}

	static void solve(int[][] mat, int n) {
		Node srcNode = new Node(mat, calHVal(mat), null, -1, -1);
		printNode(srcNode);

		PriorityQueue<Node> open = new PriorityQueue<>(new Checker());
		Queue<Node> closed = new ArrayDeque<>();

		int generatedNodes = 0, expandedNodes = 0;

		open.add(srcNode);
		generatedNodes++;
		
		Stack<Node> finalPath = new Stack<>();

		while (true) {

			Node node = open.poll();
			System.out.println("Node being explored :");
			printMat(node.mat);
			closed.add(node);
			expandedNodes++;

			if (isSolved(node.mat)) {
				System.out.println("SOLVED");
				//printMat(node.mat);
				System.out.println("Generated Nodes = " + generatedNodes);
				System.out.println("Expanded Nodes = " + expandedNodes);
				System.out.println("Path");
				while(node!=null) {
					Node tempNode = node.parentNode;
					finalPath.push(node);
					node = tempNode;
				}
				while(!finalPath.isEmpty()) {
					printMat(finalPath.pop().mat);
					System.out.println("---------------");
				}
				break;
			}

			// shift empty to up:
			if (node.x != 0 && node.parentX != node.x - 1) {
				String shiftPos = "up";
				Node childNode = generateNewNode(node, shiftPos, getCopy(node.mat));
				open.add(childNode);
				generatedNodes++;
				System.out.println(shiftPos);
				printNode(childNode);
			}

			// shift empty to down:
			if (node.x != n - 1 && node.parentX != node.x + 1) {
				String shiftPos = "down";
				Node childNode = generateNewNode(node, shiftPos, getCopy(node.mat));
				open.add(childNode);
				generatedNodes++;
				System.out.println(shiftPos);
				printNode(childNode);
			}

			// shift empty to left:
			if (node.y != 0 && node.parentY != node.y - 1) {
				String shiftPos = "left";
				Node childNode = generateNewNode(node, shiftPos, getCopy(node.mat));
				open.add(childNode);
				generatedNodes++;
				System.out.println(shiftPos);
				printNode(childNode);
			}

			// shift empty to down:
			if (node.y != n - 1 && node.parentY != node.y + 1) {
				String shiftPos = "right";
				Node childNode = generateNewNode(node, shiftPos, getCopy(node.mat));
				open.add(childNode);
				generatedNodes++;
				System.out.println(shiftPos);
				printNode(childNode);
			}
		}
	}

	static Node generateNewNode(Node parentNode, String shiftPos, int parentMat[][]) {
		int row = -1, col = -1;

		if (shiftPos == "up") {
			row = parentNode.x - 1;
			col = parentNode.y;
		} else if (shiftPos == "down") {
			row = parentNode.x + 1;
			col = parentNode.y;
		} else if (shiftPos == "left") {
			row = parentNode.x;
			col = parentNode.y - 1;
		} else if (shiftPos == "right") {
			row = parentNode.x;
			col = parentNode.y + 1;
		}

		int temp = parentMat[row][col];
		parentMat[row][col] = parentMat[parentNode.x][parentNode.y];
		parentMat[parentNode.x][parentNode.y] = temp;

		Node newNode = new Node(parentMat, calHVal(parentMat), parentNode, parentNode.x, parentNode.y);
		return newNode;
	}

	static int calHVal(int arr[][]) {
		int count = 0;
		int n=3;
		int[][] target = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		int pos = 0;
		// int[][] target = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				//pos = 3 * i + j;
				int row,col;
				if(arr[i][j]==0) {
					row = 2;
					col=2;
				}else {
					row = arr[i][j] / 3;
					col = (arr[i][j] % 3) - 1;
					if (col==-1) {
						row = row - 1;
						col = 2;
					}
				}
	
				int amt = Math.abs(row - i) + Math.abs(col - j);
				// System.out.println(amt);
				count += amt;
			}
		}
		return count;
	}

	static boolean isSolved(int mat[][]) {
		int finalMat[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (mat[i][j] != finalMat[i][j])
					return false;
			}
		}
		return true;
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