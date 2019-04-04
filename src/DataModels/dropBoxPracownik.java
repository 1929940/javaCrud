package DataModels;

public class dropBoxPracownik {

    private final int id;
    private final String nazwisko;
    private final String imie;

    public dropBoxPracownik(int id, String nazwisko, String imie) {
        this.id = id;
        this.nazwisko = nazwisko;
        this.imie = imie;
    }

    @Override
    public String toString(){
        return nazwisko + " " + imie;
    }

    public int getId() {
        return id;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getImie() {
        return imie;
    }

    // stores id, name, surname
    // override tostring to display surname + name
    // some method that returns just id
}
