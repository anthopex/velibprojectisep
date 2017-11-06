package database;

public class Stations extends Main {
    protected int nb;
    protected float lat, lng;

    public Stations(int nb, float lat, float lng) {
        this.nb = nb;
        this.lng = lat;
        this.lat = lng;
    }
}
