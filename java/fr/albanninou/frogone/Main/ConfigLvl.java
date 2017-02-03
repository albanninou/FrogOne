package fr.albanninou.frogone.Main;


public class ConfigLvl {

    public static int lvlmax = 18;

    public static char[][] getGrille(int lvl) {
        char[][][] map = new char[100][100][100];

        map[0] = new char[][]{{'A', 'A', 'V', 'A'}};

        map[1] = new char[][]{{'A', 'A', 'V', 'A'}};

        map[2] = new char[][]{{'A'}, {'A'}, {'V'}, {'A'}};

        map[3] = new char[][]{
                {'A', 'V', 'A', 'V', 'V'},
                {'V', 'A', 'V', 'V', 'V'},
                {'V', 'V', 'V', 'V', 'V'},
                {'V', 'V', 'V', 'A', 'V'},
                {'V', 'V', 'V', 'V', 'A'}};

        map[4] = new char[][]{
                {'A', 'V', 'A', 'V', 'V'},
                {'V', 'A', 'V', 'V', 'V'},
                {'B', 'B', 'V', 'B', 'B'},
                {'V', 'V', 'V', 'A', 'V'},
                {'V', 'V', 'B', 'V', 'A'}};

        map[5] = new char[][]{
                {'A', 'V', 'A', 'V', 'V'},
                {'V', 'A', 'C', 'V', 'C'},
                {'B', 'B', 'V', 'B', 'B'},
                {'V', 'V', 'V', 'A', 'V'},
                {'C', 'V', 'B', 'V', 'A'}};

        map[6] = new char[][]{
                {'A', 'A', 'B', 'C', 'A'},
                {'V', 'V', 'B', 'V', 'V'},
                {'D', 'C', 'V', 'D', 'D'},
                {'C', 'E', 'B', 'E', 'E'}};

        map[7] = new char[][]{
                {'A', 'V', 'B', 'V', 'A'},
                {'V', 'A', 'C', 'A', 'D'},
                {'D', 'B', 'V', 'B', 'V'},
                {'V', 'A', 'V', 'A', 'D'},
                {'A', 'C', 'A', 'C', 'A'}};

        map[8] = new char[][]{
                {'D', 'V', 'B', 'E', 'C'},
                {'V', 'V', 'D', 'V', 'V'},
                {'B', 'A', 'V', 'A', 'E'},
                {'E', 'C', 'D', 'B', 'E'},
                {'C', 'V', 'E', 'E', 'A'}};

        map[9] = new char[][]{
                {'E', 'A', 'B', 'D'},
                {'A', 'B', 'B', 'V'},
                {'A', 'D', 'E', 'C'},
                {'D', 'C', 'C', 'E'}};

        map[10] = new char[][]{
                {'A', 'B', 'C', 'B', 'D'},
                {'V', 'A', 'E', 'D', 'V'},
                {'D', 'C', 'E', 'C', 'A'},
                {'V', 'A', 'V', 'D', 'V'},
                {'A', 'V', 'B', 'E', 'D'}};

        map[11] = new char[][]{
                {'V', 'B', 'V', 'D', 'V'},
                {'A', 'B', 'E', 'D', 'D'},
                {'B', 'A', 'E', 'B', 'B'},
                {'D', 'D', 'V', 'D', 'A'},
                {'C', 'C', 'A', 'V', 'B'},
                {'E', 'A', 'C', 'A', 'V'}};

        map[12] = new char[][]{
                {'D', 'V', 'E', 'V', 'E', 'V', 'C'},
                {'V', 'C', 'D', 'E', 'D', 'V', 'V'},
                {'E', 'D', 'C', 'A', 'V', 'D', 'E'},
                {'V', 'E', 'A', 'V', 'A', 'E', 'V'},
                {'E', 'D', 'V', 'A', 'C', 'D', 'E'},
                {'V', 'V', 'D', 'E', 'D', 'C', 'V'},
                {'A', 'V', 'E', 'V', 'E', 'V', 'D'}};

        map[13] = new char[][]{
                {'A', 'A', 'V', 'C', 'C', 'A'},
                {'E', 'B', 'D', 'A', 'A', 'B'},
                {'C', 'E', 'C', 'V', 'B', 'V'},
                {'C', 'E', 'V', 'D', 'V', 'D'},
                {'V', 'C', 'V', 'C', 'C', 'V'},
                {'A', 'C', 'B', 'V', 'B', 'B'}};

        map[14] = new char[][]{
                {'D', 'D', 'E', 'B', 'E', 'V', 'A'},
                {'V', 'V', 'B', 'V', 'B', 'V', 'A'},
                {'B', 'E', 'D', 'E', 'A', 'E', 'B'},
                {'E', 'V', 'B', 'V', 'B', 'V', 'E'},
                {'B', 'E', 'A', 'E', 'D', 'E', 'B'},
                {'A', 'V', 'B', 'V', 'B', 'V', 'V'},
                {'A', 'V', 'E', 'B', 'E', 'D', 'D'}};

        map[15] = new char[][]{
                {'A', 'A', 'C', 'C', 'V', 'A', 'V', 'C'},
                {'B', 'V', 'E', 'V', 'A', 'B', 'B', 'V'},
                {'C', 'E', 'C', 'V', 'B', 'V', 'D', 'E'},
                {'C', 'E', 'V', 'V', 'E', 'D', 'V', 'V'},
                {'D', 'C', 'E', 'C', 'C', 'E', 'A', 'V'},
                {'A', 'C', 'E', 'A', 'E', 'B', 'B', 'A'},
                {'C', 'V', 'D', 'B', 'V', 'C', 'A', 'V'},
                {'V', 'V', 'B', 'D', 'D', 'V', 'A', 'V'}};

        map[16] = new char[][]{
                {'V', 'E', 'E', 'V', 'D', 'V', 'E', 'E'},
                {'V', 'B', 'B', 'E', 'C', 'E', 'B', 'V'},
                {'B', 'E', 'A', 'A', 'C', 'A', 'A', 'B'},
                {'B', 'E', 'B', 'B', 'V', 'D', 'D', 'E'},
                {'E', 'V', 'A', 'A', 'B', 'E', 'E', 'V'},
                {'V', 'V', 'B', 'B', 'V', 'A', 'C', 'E'},
                {'C', 'A', 'A', 'D', 'V', 'D', 'A', 'E'},
                {'B', 'V', 'C', 'C', 'A', 'V', 'D', 'V'}};

        map[17] = new char[][]{
                {'B', 'E', 'E', 'B', 'E', 'E', 'A', 'C'},
                {'D', 'B', 'A', 'D', 'C', 'B', 'C', 'C'},
                {'V', 'V', 'A', 'A', 'D', 'D', 'V', 'V'},
                {'E', 'A', 'B', 'C', 'V', 'B', 'E', 'A'},
                {'V', 'A', 'B', 'E', 'B', 'C', 'C', 'E'},
                {'C', 'C', 'E', 'C', 'E', 'E', 'V', 'B'},
                {'B', 'A', 'C', 'B', 'A', 'A', 'E', 'A'},
                {'B', 'A', 'C', 'B', 'D', 'D', 'E', 'B'}};

        map[18] = new char[][]{
                {'V', 'A', 'V', 'B', 'B', 'A', 'V', 'D'},
                {'C', 'A', 'B', 'C', 'B', 'C', 'C', 'E'},
                {'C', 'E', 'C', 'V', 'V', 'A', 'C', 'D'},
                {'E', 'V', 'D', 'B', 'C', 'V', 'B', 'D'},
                {'B', 'B', 'D', 'C', 'B', 'E', 'A', 'B'},
                {'V', 'E', 'E', 'V', 'V', 'A', 'D', 'A'},
                {'E', 'B', 'D', 'B', 'C', 'A', 'E', 'D'},
                {'C', 'A', 'D', 'V', 'D', 'D', 'E', 'D'}};


        return map[lvl];
    }

    public static int[] getLc(int lvl) {
        int[][] lc = new int[100][2];

        lc[0] = new int[]{1, 4};

        lc[1] = new int[]{1, 4};

        lc[2] = new int[]{4, 1};

        lc[3] = new int[]{5, 5};

        lc[4] = new int[]{5, 5};

        lc[5] = new int[]{5, 5};

        lc[6] = new int[]{4, 5};

        lc[7] = new int[]{5, 5};

        lc[8] = new int[]{5, 5};

        lc[9] = new int[]{4, 4};

        lc[10] = new int[]{5, 5};

        lc[11] = new int[]{6, 5};

        lc[12] = new int[]{7, 7};

        lc[13] = new int[]{6, 6};

        lc[14] = new int[]{7, 7};

        lc[15] = new int[]{8, 8};

        lc[16] = new int[]{8, 8};

        lc[17] = new int[]{8, 8};

        lc[18] = new int[]{8, 8};


        return lc[lvl];
    }


}
