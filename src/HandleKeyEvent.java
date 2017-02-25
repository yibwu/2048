import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class HandleKeyEvent {
    // if no change, DO NOT generate random block.
    private boolean changed;
    private int currentScore;
    private int highestScore;
    private int blankLeft;
    private int[][] butBitMap;
    
    public HandleKeyEvent() {
        changed = false;
        currentScore = 0;
        blankLeft = 16;
        butBitMap = new int[4][4];
        generateRandomBlock(); 
    }
    
    public int[][] getBitMap() {
        return butBitMap;
    }
    
    private boolean isChanged() {
        return changed;
    }
    
    private void setChanged(boolean f) {
        changed = f;
    }
     
    private void initGame() {
        changed = false;
        currentScore = 0;
        blankLeft = 16;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                butBitMap[i][j] = 0;
            }
        }
        generateRandomBlock(); 
    }

    private boolean isDead() {
        if (blankLeft == 0) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (butBitMap[i][j] == butBitMap[i][j + 1]) {
                        return false;
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (butBitMap[i][j] == butBitMap[i + 1][j]) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    private void recomputePressUp() {
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (butBitMap[i][j] != 0) {
                    int row = i - 1;
                    while (row > 0 && butBitMap[row][j] == 0) {
                        row--;
                    }
                    
                    if (butBitMap[row][j] == 0) {
                        butBitMap[row][j] = butBitMap[i][j];
                        butBitMap[i][j] = 0;
                        changed = true;
                    } else {
                        if (butBitMap[row][j] == butBitMap[i][j]) {
                            butBitMap[row][j] <<= 1;
                            currentScore += butBitMap[row][j];
                            butBitMap[i][j] = 0;
                            changed = true;
                            blankLeft++;
                        } else {
                            butBitMap[row + 1][j] = butBitMap[i][j];
                            if (row + 1 != i) {
                                butBitMap[i][j] = 0;
                                changed = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void recomputePressDown() {
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (butBitMap[i][j] != 0) {
                    int row = i + 1;
                    while (row < 3 && butBitMap[row][j] == 0) {
                        row++;
                    }
                   
                    if (butBitMap[row][j] == 0) {
                        butBitMap[row][j] = butBitMap[i][j];
                        butBitMap[i][j] = 0;
                        changed = true;
                    } else {
                        if (butBitMap[row][j] == butBitMap[i][j]) {
                            butBitMap[row][j] <<= 1;
                            currentScore += butBitMap[row][j];
                            butBitMap[i][j] = 0;
                            changed = true;
                            blankLeft++;
                        } else {
                            butBitMap[row - 1][j] = butBitMap[i][j];
                            if (row - 1 != i) {
                                butBitMap[i][j] = 0;
                                changed = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void recomputePressLeft() {
        for (int j = 1; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                if (butBitMap[i][j] != 0) {
                    int col = j - 1;
                    while (col > 0 && butBitMap[i][col] == 0) {
                        col--;
                    }
                    
                    if (butBitMap[i][col] == 0) {
                        butBitMap[i][col] = butBitMap[i][j];
                        butBitMap[i][j] = 0;
                        changed = true;
                    } else {
                        if (butBitMap[i][col] == butBitMap[i][j]) {
                            butBitMap[i][col] <<= 1;
                            currentScore += butBitMap[i][col];
                            butBitMap[i][j] = 0;
                            changed = true;
                            blankLeft++;
                        } else {
                            butBitMap[i][col + 1] = butBitMap[i][j];
                            if (col + 1 != j) {
                                butBitMap[i][j] = 0;
                                changed = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void recomputePressRight() {
        for (int j = 2; j >= 0; j--) {
            for (int i = 0; i < 4; i++) {
                if (butBitMap[i][j] != 0) {
                    int col = j + 1;
                    while (col < 3 && butBitMap[i][col] == 0) {
                        col++;
                    }
                    
                    if (butBitMap[i][col] == 0) {
                        butBitMap[i][col] = butBitMap[i][j];
                        butBitMap[i][j] = 0;
                        changed = true;
                    } else {
                        if (butBitMap[i][col] == butBitMap[i][j]) {
                            butBitMap[i][col] <<= 1;
                            currentScore += butBitMap[i][col];
                            butBitMap[i][j] = 0;
                            changed = true;
                            blankLeft++;
                        } else {
                            butBitMap[i][col - 1] = butBitMap[i][j]; 
                            if (col - 1 != j) {
                                butBitMap[i][j] = 0;
                                changed = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean handlePressUp() {
        if (!isDead()) {
            recomputePressUp();
            if (isChanged()) {
                generateRandomBlock();
                setChanged(false);
            } else {
                ;
            }
            return false;
        } else {
            System.out.println("Game Over! Try Again!");
            saveHighestScoreToFile();
            initGame();
            return true;
        }
    }

    public boolean handlePressDown() {
        if (!isDead()) {
            recomputePressDown();
            if (isChanged()) {
                generateRandomBlock();
                setChanged(false);
            } else {
                ;
            }
            return false;
        } else {
            System.out.println("Game Over! Try Again!");
            saveHighestScoreToFile();
            initGame();
            return true;
        }
    }

    public boolean handlePressLeft() {
        if (!isDead()) {
            recomputePressLeft();
            if (isChanged()) {
                generateRandomBlock();
                setChanged(false);
            } else {
                ;
            }
            return false;
        } else {
            System.out.println("Game Over! Try Again!");
            saveHighestScoreToFile();
            initGame();
            return true;
        }
    }

    public boolean handlePressRight() {
        if (!isDead()) {
            recomputePressRight();
            if (isChanged()) {
                generateRandomBlock();
                setChanged(false);
            } else {
                ;
            }
            return false;
        } else {
            System.out.println("Game Over! Try Again!");
            saveHighestScoreToFile();
            initGame();
            return true;
        }
    }
    
    private void generateRandomBlock() {
        Random rand = new Random();
        int x = rand.nextInt(16);
        int row = x >> 2;
        int col = x % 4;
        
        while (butBitMap[row][col] != 0) {
            x = rand.nextInt(16);
            row = x >> 2;
            col = x % 4;
        }
        /*
         *  3/4 probability generate 2's block
         *  1/4 probability generate 4's block
         */
        int y = rand.nextInt(16);
        if (y < 12) {
            butBitMap[row][col] = 2;
        } else {
            butBitMap[row][col] = 4;
        }
        blankLeft--;
    }

    public int getCurrentScore() {
        return currentScore;
    }
    
    public void saveHighestScoreToFile() {
        int highestScoreFromFile = getHighestScoreFromFile();
        BufferedWriter bw = null;
        File file = new File("highest_score.txt");

        if (highestScoreFromFile < currentScore) {
            try {
                bw = new BufferedWriter(new FileWriter(file));
                bw.write(String.valueOf(currentScore));
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            } 
        } else {
            ;
        }
    }

    public int getHighestScoreFromFile() {
        int highestScoreFromFile = 0;
        BufferedReader br = null;
        BufferedWriter bw = null;
        File file = new File("highest_score.txt");

        if (file.exists()) {
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                highestScoreFromFile = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                file.createNewFile();
                bw = new BufferedWriter(new FileWriter(file));
                bw.write(String.valueOf(highestScoreFromFile));
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
        return highestScoreFromFile;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }
    
    public void saveGame() {
        BufferedWriter bw = null;
        File file = new File("save_game.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(String.valueOf(changed));
            bw.newLine();
            bw.write(String.valueOf(currentScore));
            bw.newLine();
            bw.write(String.valueOf(blankLeft));
            bw.newLine();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    bw.write(String.valueOf(butBitMap[i][j] + " "));
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    public void recoverGame() {
        File file = new File("save_game.txt");
        BufferedWriter bw = null;
        Scanner sc = null;

        try {
            if (file.exists()) {
                sc = new Scanner(file);
                changed = sc.nextBoolean();
                currentScore = sc.nextInt();
                blankLeft = sc.nextInt();
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        butBitMap[i][j] = sc.nextInt();
                    }
                }
                sc.close();
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw = new BufferedWriter(new FileWriter(file));
                    bw.write(String.valueOf(changed));
                    bw.newLine();
                    bw.write(String.valueOf(currentScore));
                    bw.newLine();
                    bw.write(String.valueOf(blankLeft));
                    bw.newLine();
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            bw.write(String.valueOf(butBitMap[i][j] + " "));
                        }
                        bw.newLine();
                    }
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
    }
}
