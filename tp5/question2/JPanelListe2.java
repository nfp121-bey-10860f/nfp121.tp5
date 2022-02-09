package question2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Map;
import java.util.EmptyStackException;

public class JPanelListe2 extends JPanel implements ActionListener, ItemListener {

    private JPanel cmd = new JPanel();
    private JLabel afficheur = new JLabel();
    private JTextField saisie = new JTextField();

    private JPanel panelBoutons = new JPanel();
    private JButton boutonRechercher = new JButton("rechercher");
    private JButton boutonRetirer = new JButton("retirer");

    private CheckboxGroup mode = new CheckboxGroup();
    private Checkbox ordreCroissant = new Checkbox("croissant", mode, false);
    private Checkbox ordreDecroissant = new Checkbox("dé?croissant", mode, false);

    private JButton boutonOccurrences = new JButton("occurrence");

    private JButton boutonAnnuler = new JButton("annuler");

    private TextArea texte = new TextArea();

    private List<String> liste;
    private Map<String, Integer> occurrences;
    
    private Caretaker caretaker = new Caretaker();
    
    private Originator originator = new Originator();

    public JPanelListe2(List<String> liste, Map<String, Integer> occurrences) {
        this.liste = liste;
        this.occurrences = occurrences;

        cmd.setLayout(new GridLayout(3, 1));

        cmd.add(afficheur);
        cmd.add(saisie);

        panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBoutons.add(boutonRechercher);
        panelBoutons.add(boutonRetirer);
        panelBoutons.add(new JLabel("tri du texte :"));
        panelBoutons.add(ordreCroissant);
        panelBoutons.add(ordreDecroissant);
        panelBoutons.add(boutonOccurrences);
        panelBoutons.add(boutonAnnuler);
        cmd.add(panelBoutons);

        if(liste!=null && occurrences!=null){
            afficheur.setText(liste.getClass().getName() + " et "+ occurrences.getClass().getName());
            texte.setText(liste.toString());
        }else{
            texte.setText("la classe Chapitre2CoreJava semble incomplète");
        }

        setLayout(new BorderLayout());

        add(cmd, "North");
        add(texte, "Center");

        boutonAnnuler.addActionListener(this);
        boutonRechercher.addActionListener(this);
        boutonOccurrences.addActionListener(this);
        boutonRetirer.addActionListener(this);
        ordreCroissant.addItemListener(this);
        ordreDecroissant.addItemListener(this);
    }

    boolean res = false;
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == boutonRechercher || ae.getSource() == saisie) {
                res = liste.contains(saisie.getText());
                Integer occur = occurrences.get(saisie.getText());
                afficheur.setText("ré?sultat de la recherche de : "
                    + saisie.getText() + " -->  " + res);
                res = false;
            } else if (ae.getSource() == boutonRetirer) {
                originator.set(liste);
                caretaker.addMemento(originator.storeInMemento());
                res = retirerDeLaListeTousLesElementsCommencantPar(saisie.getText());
                afficheur.setText("ré?sultat du retrait de tous les é?lé?ments commençant par -->  "+ saisie.getText() + " : " + res);
            } else if (ae.getSource() == boutonOccurrences) {
                Integer occur = occurrences.get(saisie.getText());
                if (occur != null)
                    afficheur.setText(" -->  " + occur + " occurrence(s)");
                else
                    afficheur.setText(" -->  ??? ");
            } else if (ae.getSource() == boutonAnnuler) {
                List <String> temp = new LinkedList<>();
                temp = originator.restoreFromMemento(caretaker.getMemento());
                liste.clear();
                liste.addAll(temp);
            }
            texte.setText(liste.toString());
        } catch (EmptyStackException ese) {
            afficheur.setText("Stack is empty");
        }catch (Exception e) {
            afficheur.setText(e.toString());
        }
    }

    public void itemStateChanged(ItemEvent ie) {
        if (ie.getSource() == ordreCroissant){
            originator.set(liste);
            caretaker.addMemento(originator.storeInMemento());
            Collections.sort(liste);
            texte.setText(liste.toString());
        }    
        else if (ie.getSource() == ordreDecroissant){
            originator.set(liste);
            caretaker.addMemento(originator.storeInMemento());
            Collections.sort(liste, Collections.reverseOrder());
            texte.setText(liste.toString());
        }
        
        texte.setText(liste.toString());
    }

    private boolean retirerDeLaListeTousLesElementsCommencantPar(String prefixe) {
        boolean resultat = false;
        
        for(int i = 0; i < liste.size(); i++){
            if(Pattern.matches(prefixe+"*", liste.get(i))){
                liste.remove(liste.get(i));
                resultat = true;
            }
        }
        
        occurrences = Chapitre2CoreJava2.occurrencesDesMots(liste);
        texte.setText(liste.toString());
            
        return resultat;
    }
    
    public class Memento{
         private List<String> list;
         
         public Memento(List<String> list){
             this.list = list;
         }
         
         public List<String> getSavedList(){
             return list;
         }
    }
    
    public class Originator{
        private List<String> list = new LinkedList();
        
        public void set(List<String> l){
            this.list.clear();
            this.list.addAll(l);
        }
        
        public Memento storeInMemento(){
            List<String> temp = new LinkedList();
            temp.addAll(list);
            
            return new Memento(temp);
        }
        
        public List<String> restoreFromMemento(Memento memento){
            list = memento.getSavedList();
            return list;
        }
    }
    
    public class Caretaker{
        Stack<Memento> savedList = new Stack<Memento>();
        
        public void addMemento(Memento m){
            savedList.push(m);
        }
        
        public Memento getMemento(){
            return savedList.pop();
        }
        
    }
}