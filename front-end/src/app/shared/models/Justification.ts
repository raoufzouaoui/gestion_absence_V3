import { Absence } from "./Absence";

export class Justification{
  id!:number;
  Date_deb!:Date;
  date_fin!:Date;
  description!:string;
  absence!:Absence;

  constructor(Date_deb:Date,
    date_fin:Date,
    description:string,
    absence:Absence) {
      this.Date_deb=Date_deb;
      this.date_fin=date_fin;
      this.description=description;
      this.absence=absence;
  }

}


