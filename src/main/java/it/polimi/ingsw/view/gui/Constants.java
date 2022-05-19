package it.polimi.ingsw.view.gui;

public class Constants {
    //SCENES
    public static final String NICKNAME_SCENE = "/FXML/NickNameRequestScene.fxml";
    public static final String GAME_SETTINGS_SCENE = "/FXML/GameSettingsScene.fxml";
    public static final String WAITING_ROOM_SCENE = "/FXML/WaitingRoom.fxml";
    public static final String PLANNING_SCENE = "/FXML/PlanningScene.fxml";
    public static final String ACTION_SCENE = "/FXML/ActionScene.fxml";
    public static final String END_SCENE = "/FXML/EndScene.fxml";
    public static final String CHARACTER_INFO_SCENE = "/FXML/CharacterInfoScene.fxml";
    //EXCEPTIONS
    public static final String ALREADY_USED_CARD_EXC = "Card already drawn";
    public static final String STUDENT_NOT_PRESENT_EXC = "Student not present in Entrance";
    public static final String NO_MORE_SPACE_EXC =  "Dining room of student is already full, redo the move";
    public static final String INVALID_ISLAND_INDEX_EXC = "Island no longer exists, redo the move";
    public static final String INVALID_STEPS_EXC = "Not allowed to move mother nature this much";
    public static final String ABILITY_ALREADY_USED_EXC = "Character card has already been used in this turn";
    public static final String TOO_POOR_EXC = "You don't have enough coins to use this character";
    public static final String INVALID_CHARACTER_EXC = "This character is not available in this match";
    public static final String INVALID_NUM_STUDENTS_EXC = "Selected invalid number of students";
    public static final String STUDENT_NOT_PRESENT_IN_CHARACTER_EXC = "Student not present on this character card";
    public static final String INVALID_CHAR_ISLAND_EXC = "This island no longer exists";
    public static final String CLOUD_ALREADY_CHOSEN_EXCEPTION = "This cloud is already chosen by another player";
    public static final String INVALID_PHASE_EXC = "Anomaly";
    public static final String INVALID_USERNAME_EXC = "Username already chosen, please take another one";
}
