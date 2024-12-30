import React from 'react';
import { AuthProvider, TAuthConfig } from "react-oauth2-code-pkce"
import ReportPage from './components/ReportPage';

const authConfig: TAuthConfig = {
  clientId: process.env.REACT_APP_KEYCLOAK_CLIENT_ID||"",
  authorizationEndpoint: `${process.env.REACT_APP_KEYCLOAK_URL}/realms/${process.env.REACT_APP_KEYCLOAK_REALM}/protocol/openid-connect/auth`,
  tokenEndpoint: `${process.env.REACT_APP_KEYCLOAK_URL}/realms/${process.env.REACT_APP_KEYCLOAK_REALM}/protocol/openid-connect/token`,
  redirectUri: 'http://localhost:3000/',
  scope: 'openid'
};

const App: React.FC = () => {
  return (
    <AuthProvider authConfig={authConfig}>
      <div className="App">
        <ReportPage />
      </div>
    </AuthProvider>
  );
};

export default App;