package libreria;

public interface Component {
    void operation();
    void add(Component c);
    void remove(Component c);
    Component get_figlio(int i);
}
