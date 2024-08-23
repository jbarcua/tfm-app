export class Sensor {
    sensorId:String;
    catastralReference:String;
    latitude:number;
    longitude:number;
    type:String;

    constructor(sensorId:String, catastralReference:String, latitude:number, longitude:number, type:String){
        this.sensorId = sensorId;
        this.catastralReference = catastralReference;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }
}
