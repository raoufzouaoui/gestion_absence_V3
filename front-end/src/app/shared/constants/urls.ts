const BASE_URL = 'http://localhost:8080';

//Enseignant
export const ENSEIGNANT_LOGIN_URL = BASE_URL + '/api/v1/enseignant/login';
export const GET_ENSEIGNANT_BY_Email = BASE_URL + '/api/v1/enseignant/findEnseignant/byEmail/';


//Etudiant
export const GET_USER_BY_EMAIL = BASE_URL + '/api/v1/auth/find/byEmail/';
export const ETUDIANT_LOGIN_URL = BASE_URL + '/api/v1/auth/authenticate'
export const ETUDIANT_REGISTER_URL = BASE_URL + '/api/v1/auth/register';


export const GET_ALL_ETUDIANT = BASE_URL + '/api/v1/etudiant/getAllEtudiant';
export const GET_ETUDIANT_BY_GROUPE = BASE_URL + "/api/v1/etudiant/findEtudiant/byGroupe/";



export const GET_ETUDIANT_BY_ID = BASE_URL + '/api/v1/etudiant/findEtudiant/byId/';
export const GET_ETUDIANT_BY_Email = BASE_URL + '/api/v1/etudiant/findEtudiant/byEmail/';




// export const ADD_ETUDIANT = BASE_URL + '/etudiant/add';
// export const UPDATE_ETUDIANT = BASE_URL + '/etudiant/update/';
// export const DELETE_ETUDIANT = BASE_URL + '/etudiant/delete/';

//justification
export const ADD_JUSTIFICATION = BASE_URL + '/api/v1/justification/addJustification'
export const GET_ALL_JUSTIFICATION = BASE_URL + '/api/v1/justification/getAllJustification'
export const GET_JUSTIFICATION_BY_ID_ABSENCE = BASE_URL + '/api/v1/justification/findJustification/byAbsenceId/'
export const GET_JUSTIFICATION_BY_ID_ENSEIGNANT = BASE_URL + '/api/v1/justification/findJustification/ByEnseignantId/'

//Absence
export const GET_ALL_ABSENCE = BASE_URL + '/api/v1/absence/getAllAbsence';
export const GET_ABSENCE_BY_ID_Etudiant = BASE_URL + '/api/v1/absence/findAbsence/byEtudiantId/';
export const ADD_ABSENCE = BASE_URL + '/api/v1/absence/addAbsence';

export const GET_ABSENCE_BY_ID = BASE_URL + '/api/v1/absence/findAbsence/byId/';
export const UPDATE_ABSENCE = BASE_URL + '/api/v1/absence/updateAbsence'

//Matiere
export const GET_MATIERE_BY_ID = BASE_URL + '/api/v1/matiere/findMatiere/byId/';
export const GET_MATIERE_BY_NOM = BASE_URL + '/api/v1/matiere/findMatiere/byNom/';

