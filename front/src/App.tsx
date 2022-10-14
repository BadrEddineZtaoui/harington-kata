import React from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import OperationsContainer from "./Components/Operation/OperationsContainer";

const queryClient = new QueryClient()

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Container className={"p-4"} fluid>
        <Row>
          <Col>
            <h1 className={"pb-4"}>Bienvenue Cher Client</h1>
            <OperationsContainer />
          </Col>
        </Row>
      </Container>
    </QueryClientProvider>
  );
}

export default App;
