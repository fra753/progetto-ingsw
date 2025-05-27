package libreria;

import java.util.ArrayList;
import java.util.List;

public class Collezioni implements Component{

    private String nome;
    private List<Component> figli = new ArrayList<Component>();

    public String getNome() {
        return nome;
    }

    public int get_size(){
        return figli.size();
    }

    public List<Component> prendi_componenti() {
        return figli;
    }

    public Collezioni(String nome) {
        this.nome = nome;
    }

    @Override
    public void operation() {
        System.out.println("collezione " + nome);
        for (Component c : figli) {
            c.operation();
        }
    }

    @Override
    public void add(Component c) {
        figli.add(c);
    }

    @Override
    public void remove(Component c) {
        figli.remove(c);
    }

    @Override
    public Component get_figlio(int i) {
        return figli.get(i);
    }
}
