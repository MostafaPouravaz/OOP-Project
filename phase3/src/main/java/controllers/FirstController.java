package controllers;

public class FirstController extends Controller{
    private static FirstController instance = null;

    private FirstController() {

    }

    private static void setInstance(FirstController instance) {
        FirstController.instance = instance;
    }

    public static FirstController getInstance() {
        if (FirstController.instance == null) {
            FirstController.setInstance(new FirstController());
        }
        return FirstController.instance;
    }
}
