public class Main {
    public static void main(String[] args) {

        // instantiate and initialize our Rbt object
        Rbt tree = new Rbt();
        // load it up with some initial values
        tree.insert(5);
        tree.insert(15);
        tree.insert(2);
        // test it out
        while (true) {
            Vis.showTree(tree);
            if (!Vis.showMenu(tree)) {
                break;
            }
        }
    }
}