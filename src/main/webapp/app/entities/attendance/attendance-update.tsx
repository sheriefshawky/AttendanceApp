import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAttendanceType } from 'app/shared/model/attendance-type.model';
import { getEntities as getAttendanceTypes } from 'app/entities/attendance-type/attendance-type.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IAttendance } from 'app/shared/model/attendance.model';
import { getEntity, updateEntity, createEntity, reset } from './attendance.reducer';

export const AttendanceUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const attendanceTypes = useAppSelector(state => state.attendanceType.entities);
  const employees = useAppSelector(state => state.employee.entities);
  const attendanceEntity = useAppSelector(state => state.attendance.entity);
  const loading = useAppSelector(state => state.attendance.loading);
  const updating = useAppSelector(state => state.attendance.updating);
  const updateSuccess = useAppSelector(state => state.attendance.updateSuccess);
  const handleClose = () => {
    props.history.push('/attendance' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAttendanceTypes({}));
    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.logDate = convertDateTimeToServer(values.logDate);
    values.logTimeFrom = convertDateTimeToServer(values.logTimeFrom);
    values.logTimeTo = convertDateTimeToServer(values.logTimeTo);

    const entity = {
      ...attendanceEntity,
      ...values,
      attendanceType: attendanceTypes.find(it => it.id.toString() === values.attendanceType.toString()),
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      managerApprovedBy: employees.find(it => it.id.toString() === values.managerApprovedBy.toString()),
      hrApprovedBy: employees.find(it => it.id.toString() === values.hrApprovedBy.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          logDate: displayDefaultDateTime(),
          logTimeFrom: displayDefaultDateTime(),
          logTimeTo: displayDefaultDateTime(),
        }
      : {
          ...attendanceEntity,
          logDate: convertDateTimeFromServer(attendanceEntity.logDate),
          logTimeFrom: convertDateTimeFromServer(attendanceEntity.logTimeFrom),
          logTimeTo: convertDateTimeFromServer(attendanceEntity.logTimeTo),
          attendanceType: attendanceEntity?.attendanceType?.id,
          employee: attendanceEntity?.employee?.id,
          managerApprovedBy: attendanceEntity?.managerApprovedBy?.id,
          hrApprovedBy: attendanceEntity?.hrApprovedBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="attendanceApp.attendance.home.createOrEditLabel" data-cy="AttendanceCreateUpdateHeading">
            <Translate contentKey="attendanceApp.attendance.home.createOrEditLabel">Create or edit a Attendance</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="attendance-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('attendanceApp.attendance.logDate')}
                id="attendance-logDate"
                name="logDate"
                data-cy="logDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('attendanceApp.attendance.logTimeFrom')}
                id="attendance-logTimeFrom"
                name="logTimeFrom"
                data-cy="logTimeFrom"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('attendanceApp.attendance.logTimeTo')}
                id="attendance-logTimeTo"
                name="logTimeTo"
                data-cy="logTimeTo"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('attendanceApp.attendance.permissionHours')}
                id="attendance-permissionHours"
                name="permissionHours"
                data-cy="permissionHours"
                type="text"
              />
              <ValidatedField
                label={translate('attendanceApp.attendance.details')}
                id="attendance-details"
                name="details"
                data-cy="details"
                type="text"
              />
              <ValidatedField
                id="attendance-attendanceType"
                name="attendanceType"
                data-cy="attendanceType"
                label={translate('attendanceApp.attendance.attendanceType')}
                type="select"
              >
                <option value="" key="0" />
                {attendanceTypes
                  ? attendanceTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="attendance-employee"
                name="employee"
                data-cy="employee"
                label={translate('attendanceApp.attendance.employee')}
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="attendance-managerApprovedBy"
                name="managerApprovedBy"
                data-cy="managerApprovedBy"
                label={translate('attendanceApp.attendance.managerApprovedBy')}
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="attendance-hrApprovedBy"
                name="hrApprovedBy"
                data-cy="hrApprovedBy"
                label={translate('attendanceApp.attendance.hrApprovedBy')}
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/attendance" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AttendanceUpdate;
