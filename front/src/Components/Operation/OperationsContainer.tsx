import React, {useState} from 'react';
import Alert from 'react-bootstrap/Alert';
import OperationsList from "./OperationsList";
import OperationsForm from "./OperationsForm";

const OperationsContainer = () => {

  const [showForm, setShowForm] = useState(false);
  const [error, setError] = useState({
    show: false,
    message: ""
  });

  const handleToggleForm = () => {
    setShowForm(!showForm);
  }

  const handleError = (errorMessage: string) => {
    setError({
      show: true,
      message: errorMessage
    })
  }

  const resetError = () => {
    setError({
      show: false,
      message: ""
    })
  }

  return (
    <>
      {
        error.show &&
        <Alert variant={'danger'}>
          {error.message}
        </Alert>
      }
      {
        showForm ?
          <OperationsForm handleToggleForm={handleToggleForm} handleError={handleError} resetError={resetError}/> :
          <OperationsList handleToggleForm={handleToggleForm} />
      }
    </>
  )

}

export default OperationsContainer;
