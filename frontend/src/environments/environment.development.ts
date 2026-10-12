export const environment = {
  // backendBaseUrl: 'http://localhost:8080'

  production: true,
  // @ts-ignore
  backendBaseUrl: window['env']['backendBaseUrl'] ||  'http://localhost:8080'

};

