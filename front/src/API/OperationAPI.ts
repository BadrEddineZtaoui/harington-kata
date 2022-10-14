import {SERVER_URL} from './Constants';
import {OperationDtoType} from "../Components/Types/OperationDtoType";

export const getOperations = async (accountId: number) => {
  return fetch(`${SERVER_URL}/operation/${accountId}/history`,{
    method: "GET",
    headers: {
      "Content-Type": "application/json"
    }
  }).then(response => response.json())
    .then(data => {return data})
    .catch(error => {
      console.log(error);
    });
}

export const makeDeposit = async (operation: OperationDtoType) => {
  return fetch(`${SERVER_URL}/operation/deposit`,{
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(operation)
  }).then(response => response.json())
    .then(data => {return data})
    .catch(error => {
      console.log(error);
    });
}

export const makeWithdrawal = async (operation: OperationDtoType) => {
  return fetch(`${SERVER_URL}/operation/withdrawal`,{
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(operation)
  }).then(response => response.json())
    .then(data => {return data})
    .catch(error => {
      console.log("error",error);
    });
}