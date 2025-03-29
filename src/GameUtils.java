import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.awt.geom.RoundRectangle2D;

public class GameUtils {
    protected static JLayeredPane layeredPane = new JLayeredPane();
    protected static JPanel overlayPanel = new JPanel();

    protected static Clip backgroundMusic;
    protected static long pauseTime;

    //UI METHODS//

    /**
     * Creates a standardized GridBagConstraints for all panels
     *
     * @return configured GridBagConstraints instance
     */
    protected static GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 40, 10);
        return gbc;
    }

    /**
     * Creates a standardized label for the entire game
     *
     * @param text - Text to display
     * @param fontSize - Font size of text
     * @param color - Color of text
     * @return configured JLabel
     */
    protected static JLabel createLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Poppins", Font.BOLD, fontSize));
        label.setForeground(color);
        return label;
    }

    /**
     * Creates a standardized rounded button for the entire game
     *
     * @param text - Text to display in the button
     * @param size - Preferred button dimensions
     * @param font - Font of text
     * @param bgColor - Color of button
     * @param fgColor - Color of text
     * @param action - ActionListener for button clicks
     * @return configured JButton
     */
    protected static JButton createButton(String text, Dimension size, Font font, Color bgColor, Color fgColor, java.awt.event.ActionListener action) {
        RoundedButton button = new RoundedButton(text);
        button.setPreferredSize(size);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        if (action != null) {
            button.addActionListener(action);
        }
        return button;
    }

    /**
     * Creates a standardized panel for the entire game
     *
     * @param layout - LayoutManager to use
     * @param isOpaque - Whether the area around the panel should be opaque
     * @return configured JPanel
     */
    protected static JPanel createPanel(LayoutManager layout, boolean isOpaque) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(isOpaque);
        return panel;
    }

    /**
     * Creates a standardized panel with a return button
     *
     * @param screenName - Screen to return to
     * @param panel - The frame's main panel
     * @param cardLayout - The frame's CardLayout
     * @return configured return panel with return button
     */
    protected static JPanel createReturnPanel(String screenName, JPanel panel, CardLayout cardLayout) {
        JPanel topPanel = createPanel(new FlowLayout(FlowLayout.LEFT), false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton returnButton = createButton("Return", new Dimension(100, 50), new Font("Poppins",
                Font.BOLD, 20), new Color(0x0FFFFF), Color.BLACK, e -> cardLayout.show(panel, screenName));
        topPanel.add(returnButton);

        return topPanel;
    }

    /**
     * Creates a set of standardized difficulty selection buttons
     *
     * @param panel - Panel to add buttons to
     * @param gbc - GridBagConstraints for layout
     * @param isLeaderboard - Whether buttons should direct to the leaderboards screen (true) or start game screen (false)
     * @param game - Game class instance for calling methods within it
     */
    protected static void createGameModeButtons(JPanel panel, GridBagConstraints gbc, boolean isLeaderboard, Game game) {
        String[] difficulties = {"Easy", "Normal", "Hard"};
        for (String difficulty : difficulties) {
            JButton button = createButton(difficulty, new Dimension(400, 120),
                    new Font("Poppins", Font.BOLD, 30), new Color(0x0FFFFF), Color.BLACK,
                    e -> {
                        if (isLeaderboard) {
                            game.showLeaderboard(difficulty);
                        } else {
                            game.showGameStart(difficulty);
                        }
                    });
            panel.add(button, gbc);
        }
    }

    /**
     * Creates and displays a semi-transparent pause overlay
     *
     * @param frame - The game's frame
     * @param panel - The frame's main panel
     * @return the overlay panel
     */
    protected static JPanel showPauseOverlay(JFrame frame, JPanel panel) {
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.add(panel, JLayeredPane.DEFAULT_LAYER);
        frame.setContentPane(layeredPane);

        overlayPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }

        };
        overlayPanel.setOpaque(false);
        overlayPanel.setBounds(0, 0, panel.getWidth(), panel.getHeight());

        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);
        frame.setContentPane(layeredPane);
        frame.revalidate();
        frame.repaint();

        return overlayPanel;
    }

    /**
     * Removes the pause overlay
     */
    protected static void removePauseOverlay() {
        layeredPane.remove(overlayPanel);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    /**
     * Re-enables game controls after resuming
     *
     * @param pauseButton - Pause button
     * @param optionButtons - Options/Choices for each question
     * @return true
     */
    protected static boolean resumeGame(JButton pauseButton, JButton[] optionButtons) {
        pauseButton.setEnabled(true);
        for (JButton button : optionButtons) {
            button.setEnabled(true);
        }
        return true;
    }

    /**
     * Displays confirmation dialog before exiting the application while playing
     */
    protected static void exitWarning () {
        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Warning", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
        if (option == JOptionPane.NO_OPTION) {
        }
    }


    //MUSIC METHODS//

    /**
     * Plays background music from specified file in continuous loop
     *
     * @param filePath - path to audio file
     */
    protected static void playBackgroundMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            if (musicFile.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioInputStream);
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundMusic.start();
            } else {
                System.out.println("Background music file not found: " + filePath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pauses currently playing background music while remembering position
     */
    protected static void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            pauseTime = backgroundMusic.getMicrosecondPosition();
            backgroundMusic.stop();
        }
    }

    /**
     * Resumes background music from last paused position
     */
    protected static void resumeBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isRunning() && pauseTime > 0) {
            backgroundMusic.setMicrosecondPosition(pauseTime);
            backgroundMusic.start();
        }
    }

    /**
     * Completely stops background music
     */
    protected static void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.close();
            pauseTime = 0;
        }
    }

    /**
     * Toggles background music playback state between:
     * - Play (if stopped)
     * - Pause (if playing)
     * - Resume (if paused)
     */
    protected static void toggleBackgroundMusic() {
        if (backgroundMusic == null || !backgroundMusic.isOpen()) {
            playBackgroundMusic("Assets//Itty Bitty.wav");
        } else if (backgroundMusic.isRunning()) {
            pauseBackgroundMusic();
        } else {
            resumeBackgroundMusic();
        }
    }


    //DATA MANIPULATION METHODS//

    /**
     * Manages leaderboard scores for different difficulty levels
     * Creates a "scores" directory if it doesn't exist and maintains separate score files for each difficulty
     * Saves files stored in .txt files
     * Scores format "username:score" (saved per line)
     * For existing users, updates their score only if the new score is higher
     * For new users, adds their score to the leaderboard
     *
     * @param difficulty - Game difficulty level
     * @param username - Player's username
     * @param score - Player's current score
     */
    protected static void addScoreToLeaderboard(String difficulty, String username, int score) {
        List<PlayerScore> scores = new ArrayList<>();
        String filePath = "scores//" + difficulty.toLowerCase() + " mode scores.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(":");
                if (variables.length == 2) {
                    String existingUsername = variables[0];
                    int existingScore = Integer.parseInt(variables[1]);
                        scores.add(new PlayerScore(existingUsername, existingScore));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean userExists = false;
        for (PlayerScore playerScore : scores) {
            if (playerScore.getUsername().equals(username)) {
                userExists = true;
                if (score > playerScore.getScore()) {
                    playerScore.score = score;
                }
                break;
            }
        }

        if (!userExists) {
            scores.add(new PlayerScore(username, score));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (PlayerScore playerScore : scores) {
                writer.write(playerScore.getUsername() + ":" + playerScore.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves and sorts player scores for a specific difficulty
     * Scores are returned in descending order
     *
     * @param difficulty - Game difficulty to retrieve scores for
     * @return list of sorted player scores
     */
    protected static List<PlayerScore> getPlayerScore(String difficulty) {
        List<PlayerScore> scores = new ArrayList<>();
        String filePath = "scores//" + difficulty.toLowerCase() + " mode scores.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(":");
                if (variables.length == 2) {
                    String username = variables[0];
                    int score = Integer.parseInt(variables[1]);
                    scores.add(new PlayerScore(username, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scores.sort((playerScore1, playerScore2) -> Integer.compare(playerScore2.getScore(), playerScore1.getScore()));
        return scores;
    }


    /**
     * Saves the current game state for a player
     * Creates a "saves" directory if it doesn't exist and maintains separate save files for each difficulty
     * Save files stored in .txt files
     * Save format: "username:score:lives:questionIndex" (saved per line)
     * Only one save slot is allowed per player - new saves overwrite previous ones
     *
     * @param username - Player's username to save
     * @param score - Current score to save
     * @param lives - Remaining lives to save
     * @param questionIndex - Current question index/position to save
     * @param difficulty - Game difficulty level to save to
     * @param panel - UI navigation
     * @param cardLayout - Screen Transitions
     */
    protected static void saveGameState(String username, int score, int lives, int questionIndex,
                                        String difficulty, JPanel panel, CardLayout cardLayout) {
        File savesDirectory = new File("saves");
        if (!savesDirectory.exists()) {
            savesDirectory.mkdirs();
        }

        String saveFilePath = "saves//" + difficulty.toLowerCase() + " mode saves.txt";
        File saveFile = new File(saveFilePath);

        List<String> lines = new ArrayList<>();
        boolean userExists = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(":");
                if (variables.length == 4 && variables[0].equals(username)) {
                    lines.add(username + ":" + score + ":" + lives + ":" + questionIndex);
                    userExists = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to read save file",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!userExists) {
            lines.add(username + ":" + score + ":" + lives + ":" + questionIndex);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Please note that you only get one save at a time",
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            int option = JOptionPane.showConfirmDialog(null, "Game saved successfully. " +
                    "Would you like to return to the Main Menu?",
                    "Game Saved", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                stopBackgroundMusic();
                removePauseOverlay();
                cardLayout.show(panel, "StartScreen");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to save the game",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Deletes a player's saved game state for the specified difficulty
     * Searches the save file for the given username and removes their save data if found
     *
     * @param username - Player's username to delete
     * @param difficulty - Difficulty level of the game's save to delete
     */
    protected static void deleteGameState(String username, String difficulty) {
        String saveFilePath = "saves//" + difficulty.toLowerCase() + " mode saves.txt";
        File saveFile = new File(saveFilePath);

        List<String> lines = new ArrayList<>();
        boolean userExists = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(":");
                if (variables.length == 4 && variables[0].equals(username)) {
                    userExists = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!userExists) {
            JOptionPane.showMessageDialog(null, "You currently have no recorded save file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(null, "Your save file has been successfully deleted",
                        "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Loads a saved game state from the game class' main menu
     * Checks for existing saves and prompts the user to either:
     * - Load their last saved game
     * - Start a new game
     *
     * @param username - Player's username to search for saves
     * @param difficulty - Difficulty level of the game to load for saves
     * @param game - The class from which to call the method
     */
    protected static void mainMenuLoadGameState(String username, String difficulty, Game game) {
        String saveFilePath = "saves//" + difficulty.toLowerCase() + " mode saves.txt";
        File file = new File(saveFilePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(saveFilePath))) {
                String line;
                boolean hasSave = false;
                while ((line = reader.readLine()) != null) {
                    String[] variables = line.split(":");
                    if (variables.length == 4 && variables[0].equals(username)) {
                        hasSave = true;
                        Object[] options = {"Yes", "Start New Game"};
                        int option = JOptionPane.showOptionDialog(null,
                                "You have a saved game for this difficulty. Do you want to load it?",
                                "Load Save",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);

                        if (option == JOptionPane.YES_OPTION) {
                            GameState gameState = new GameState(
                                    Integer.parseInt(variables[1]),
                                    Integer.parseInt(variables[2]),
                                    Integer.parseInt(variables[3])
                            );
                            game.startWithSaveState(difficulty, gameState);
                            return;
                        } else if (option == JOptionPane.NO_OPTION) {
                            game.startNewRound(difficulty);
                            return;
                        }
                        break;
                    }
                }

                if (!hasSave) {
                    game.startNewRound(difficulty);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to read the save file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            game.startNewRound(difficulty);
        }
    }



    /**
     * Loads game state during gameplay
     * Overwrites current game parameters with saved values if user chooses to load
     *
     * @param username - Player's username to search for saves
     * @param difficulty - Difficulty level of the game to load for saves
     * @param base - The class that the method will  modify
     * @return true if game was loaded, false otherwise
     */
    protected static boolean gameplayLoadGameState(String username, String difficulty, Game.Base base) {
        String saveFilePath = "saves//" + difficulty.toLowerCase() + " mode saves.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] variables = line.split(":");
                if (variables.length == 4 && variables[0].equals(username)) {
                    int loadedScore = Integer.parseInt(variables[1]);
                    int loadedLives = Integer.parseInt(variables[2]);
                    int loadedQuestionIndex = Integer.parseInt(variables[3]);

                    Object[] options = {"Yes", "No"};
                    int option = JOptionPane.showOptionDialog(null,
                            "You have a saved game for this difficulty. Do you want to load it?",
                            "Load Save", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    if (option == JOptionPane.YES_OPTION) {
                        base.score = loadedScore;
                        base.lives = loadedLives;
                        base.questionIndex = loadedQuestionIndex;

                        base.scoreLabel.setText("Score: " + loadedScore + "/15");
                        base.livesLabel.setText("Lives: " + loadedLives);

                        JOptionPane.showMessageDialog(null, "Game loaded successfully!",
                                "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                        base.loadNextQuestion();
                        return true;
                    }
                    if (option == JOptionPane.NO_OPTION) {
                        return false;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "You currently have no recorded save file",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load the game",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    //INNER CLASSES//

    /**
     * Custom JButton that renders rounded corners and hover/click effects
     */
    protected static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else {
                g2.setColor(getBackground());
            }

            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));

            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
            g2.dispose();
        }
    }

    /**
     * Custom JPanel that renders gradient backgrounds
     */
    protected static class GradientPanel extends JPanel {
        Color startColor;
        Color endColor;

        public GradientPanel(LayoutManager layout, Color startColor, Color endColor) {
            super(layout);
            this.startColor = startColor;
            this.endColor = endColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    getWidth(), getHeight(), endColor
            );
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Data container for getting save states
     */
    protected static class GameState {
        int score;
        int lives;
        int questionIndex;

        public GameState(int score, int lives, int questionIndex) {
            this.score = score;
            this.lives = lives;
            this.questionIndex = questionIndex;
        }

        public int getScore() {
            return score;
        }

        public int getLives() {
            return lives;
        }

        public int getQuestionIndex() {
            return questionIndex;
        }
    }

    /**
     * Data container for getting a player's recorded score
     */
    protected static class PlayerScore {
        String username;
        int score;

        public PlayerScore(String username, int score) {
            this.username = username;
            this.score = score;
        }

        public String getUsername() {
            return username;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return username + ": " + score;
        }
    }
}