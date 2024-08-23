export class SensorRequest {
    owner:String;
    catastralReference:String;
    latitude:number;
    longitude:number;
    type:String;

    constructor(owner:String, catastralReference:String, latitude:number, longitude:number, type:String){
        this.owner = owner;
        this.catastralReference = catastralReference;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }
}
