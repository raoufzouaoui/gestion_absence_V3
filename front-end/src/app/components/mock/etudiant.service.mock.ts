import { Observable, of } from 'rxjs';

export class EtudiantServiceMock {
  // Define a mock response data here
  private mockResponse = { success: true, message: 'Registration successful' };

  register(user: any): Observable<any> {
    // Simulate a successful registration with a delay
    return new Observable(observer => {
      setTimeout(() => {
        observer.next(this.mockResponse);
        observer.complete();
      }, 1000); // Simulate a 1-second delay
    });
  }
}
