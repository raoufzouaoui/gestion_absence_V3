import { User } from "./User";

export class Matiere{
  id!:number;
  nom!:string;
  enseignant!:Set<User>;
}


