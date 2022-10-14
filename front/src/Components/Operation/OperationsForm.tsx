import React, {useState} from 'react';
import {useMutation, useQueryClient} from '@tanstack/react-query';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {makeDeposit, makeWithdrawal} from "../../API/OperationAPI";
import {OperationDtoType} from "../Types/OperationDtoType";

const OperationsForm = (
  {handleToggleForm, handleError, resetError}:
    {handleToggleForm: () => void, handleError: (error: string) => void, resetError: () => void}
  ) => {

  const queryClient = useQueryClient();

  const [operation, setOperation] = useState({
    operationType: "DEPOSIT",
    amount: 1
  });

  const operationMutation = useMutation(
    (operationToSave: OperationDtoType) => {
      if (operationToSave.operationType === "DEPOSIT") {
        return makeDeposit(operationToSave);
      } else {
        return makeWithdrawal(operationToSave);
      }
    },
    {
      onSuccess: (data) => {
        if(data.hasOwnProperty("errorMessage")){
          handleError(data.errorMessage);
        } else {
          queryClient.invalidateQueries(["operations"]);
          queryClient.refetchQueries(["operations"], {}, {});
          resetError();
        }
      }
    }
  )

  const handleChange = (event: { target: { id: string, type: string, name: string, value: any } }) => {
    const field = event.target.name;
    const value = event.target.type === "radio" ? event.target.id : event.target.value;
    setOperation({
      ...operation,
      [field]: value
    });
  }

  const handleSubmit = () => {
    const operationToSave : OperationDtoType = {
      clientId: 1,
      accountId: 1,
      amount: parseFloat(String(operation.amount)),
      operationType: operation.operationType
    }
    operationMutation.mutate(operationToSave);
    handleToggleForm();
  }

  return (
    <>
      <h2 className={"py-2"}>Effectuer une operation</h2>
      <Form>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Montant</Form.Label>
          <Form.Control
            min={1}
            type="number"
            name="amount"
            placeholder="Montant de l'operation"
            value={operation.amount}
            onChange={handleChange}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicCheckbox">
          <Form.Check
            inline
            label="Deposit"
            name="operationType"
            type="radio"
            id="DEPOSIT"
            checked={operation.operationType === "DEPOSIT"}
            onChange={handleChange}
          />
          <Form.Check
            inline
            label="Withdrawal"
            name="operationType"
            type="radio"
            id="WITHDRAWAL"
            checked={operation.operationType === "WITHDRAWAL"}
            onChange={handleChange}
          />
        </Form.Group>
        <Button className={"m-2"} variant="primary" onClick={handleSubmit}>
          Submit
        </Button>
        <Button variant="secondary" onClick={handleToggleForm}>
          Annuler
        </Button>
      </Form>
    </>
  );
};

export default OperationsForm;
