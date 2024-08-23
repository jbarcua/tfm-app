import { Sensor } from "./sensor";

export class Layer {
    id:String;
    catastralReference:String;
    geoData: any;
    sensorList: Array<Sensor>;
}
