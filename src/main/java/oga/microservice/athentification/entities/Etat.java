package oga.microservice.athentification.entities;

public enum Etat {
    SORTIE(0),
    ENTREE(1);
    private int id;
    Etat (int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
