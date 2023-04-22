const BASE_URL = 'http://localhost:8080';

//Enseignant
export const USER_LOGIN_URL = BASE_URL + '/user/login';
export const USER_REGISTER_URL = BASE_URL + '/user/register';
export const GET_ENSEIGNANT_BY_EMAIL = BASE_URL + '/user/find/';


//Etudiant
export const ETUDIANT_LOGIN_URL = BASE_URL + '/etudiant/login'

export const GET_ETUDIANT_BY_ID = BASE_URL + '/etudiant/find/';
export const GET_ETUDIANT_BY_Email = BASE_URL + '/etudiant/find/email/';
export const GET_ALL_ETUDIANT = BASE_URL + '/etudiant/all';
export const GET_ETUDIANT_BY_GROUPE = BASE_URL + "/etudiant/find/groupe/";

export const ADD_ETUDIANT = BASE_URL + '/etudiant/add';
export const UPDATE_ETUDIANT = BASE_URL + '/etudiant/update/';
export const DELETE_ETUDIANT = BASE_URL + '/etudiant/delete/';

//justification
export const ADD_JUSTIFICATION = BASE_URL + '/justification/add'
export const GET_ALL_JUSTIFICATION = BASE_URL + '/justification/all'
export const GET_JUSTIFICATION_BY_ID_ABSENCE = BASE_URL + '/justification/find/byAbsenceId/'
export const GET_JUSTIFICATION_BY_ID_ENSEIGNANT = BASE_URL + '/justification/find/ByEnseignantId/'

//Absence
export const GET_ALL_ABSENCE = BASE_URL + '/etudiant/absence/all';
export const GET_ABSENCE_BY_ID_Etudiant = BASE_URL + '/etudiant/absence/find/byEtudiant/';
export const ADD_ABSENCE = BASE_URL + '/etudiant/absence/add';
export const GET_ABSENCE_BY_ID = BASE_URL + '/etudiant/absence/find/byId/';
export const UPDATE_ABSENCE = BASE_URL + '/etudiant/absence/update'

//Matiere
export const GET_MATIERE_BY_ID = BASE_URL + '/matiere/find/';
export const GET_MATIERE_BY_NOM = BASE_URL + '/matiere/find/nom/';

