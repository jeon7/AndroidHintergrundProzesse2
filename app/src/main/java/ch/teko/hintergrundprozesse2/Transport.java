package ch.teko.hintergrundprozesse2;

public class Transport {

    private String id;
    private String name;
    private String score;
    private String coordinate_type;
    private String coordinate_x;
    private String coordinate_y;
    private String distance;
    private String icon;
    private int drawableId;

    public Transport(String name){
        this.name = name;
    }

    public Transport(String id, String name, String score, String coordinate_type, String coordinate_x, String coordinate_y,
                     String distance, String icon, int drawableId) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.coordinate_type = coordinate_type;
        this.coordinate_x = coordinate_x;
        this.coordinate_y = coordinate_y;
        this.distance = distance;
        this.icon = icon;
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public String getCoordinate_x() {
        return coordinate_x;
    }

    public String getCoordinate_y() {
        return coordinate_y;
    }

    public int getDrawableId() {
        return drawableId;
    }

}
