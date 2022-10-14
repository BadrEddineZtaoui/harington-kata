import React from 'react';
import Table from 'react-bootstrap/Table';
import {OperationType} from "../Types/OperationType";
import {getOperations} from "../../API/OperationAPI";
import { useQuery } from '@tanstack/react-query';
import Button from "react-bootstrap/Button";
import Stack from "react-bootstrap/Stack";

const OperationsList = ({handleToggleForm}: {handleToggleForm: () => void}) => {

  const { isLoading, isError, data: operations } = useQuery(
    ['operations'],
    () => getOperations(1),
    {staleTime: 60000}
  )

  if (isLoading) return <div>Chargement de vos operations en cours...</div>

  if (isError) return <div>Une erreur est survenue resseyer plus tard !!</div>

  return (
    <>
      <Stack direction="horizontal" gap={3}>
        <h2 className={"py-2"}>Historique des operations</h2>
        <Button className={"ms-auto"} variant="primary" onClick={handleToggleForm}>Effectuer une operation</Button>
      </Stack>
      <Table striped bordered hover>
        <thead>
        <tr>
          <th>Operation Type</th>
          <th>Date</th>
          <th>Amount</th>
          <th>Balance</th>
        </tr>
        </thead>
        <tbody>
        {
          operations && operations.map((operation: OperationType) => (
            <tr key={operation.id}>
              <td>{operation.operationType}</td>
              <td>{operation.date}</td>
              <td>{operation.amount}</td>
              <td>{operation.balance}</td>
            </tr>
          ))
        }
        </tbody>
      </Table>
    </>
  );
};

export default OperationsList;
