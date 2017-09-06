public class Event {

    public Apparition apparition;
    public Recompense recompense;
    public EventAction action;

    /* Vérifie que les conditions d'apparition de l'évènement sont respectées */
    public boolean peutApparaitre(){
        return this.apparition.peutApparaitre();
    }

    /* Fait apparaitre l'évènement à l'écran */
    public void apparaitre (){

    }

    /* Declenche la récompense si l'event s'est bien déroulé */
    public void recompenser(){
        this.recompense.recompenser();
    }

    /* Getters & Setters */
    public Apparition getApparition() {
        return apparition;
    }

    public void setApparition(Apparition apparition) {
        this.apparition = apparition;
    }

    public Recompense getRecompense() {
        return recompense;
    }

    public void setRecompense(Recompense recompense) {
        this.recompense = recompense;
    }

    public EventAction getAction() {
        return action;
    }

    public void setAction(EventAction action) {
        this.action = action;
    }
}
