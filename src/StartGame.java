public class StartGame {

    public static void main(String[] args) {
        HandleKeyEvent handler = new HandleKeyEvent();
        MainFrame mf = new MainFrame(handler);
        mf.initFrame();
    }
}
