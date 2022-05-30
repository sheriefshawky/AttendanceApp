import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee.reducer';

export const EmployeeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const employeeEntity = useAppSelector(state => state.employee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDetailsHeading">
          <Translate contentKey="attendanceApp.employee.detail.title">Employee</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.id}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="attendanceApp.employee.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.nameEn}</dd>
          <dt>
            <span id="nameAr">
              <Translate contentKey="attendanceApp.employee.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.nameAr}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="attendanceApp.employee.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.email}</dd>
          <dt>
            <span id="employeeCode">
              <Translate contentKey="attendanceApp.employee.employeeCode">Employee Code</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.employeeCode}</dd>
          <dt>
            <span id="mobileNo">
              <Translate contentKey="attendanceApp.employee.mobileNo">Mobile No</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.mobileNo}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="attendanceApp.employee.address">Address</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.address}</dd>
          <dt>
            <span id="nationalId">
              <Translate contentKey="attendanceApp.employee.nationalId">National Id</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.nationalId}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="attendanceApp.employee.title">Title</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.title}</dd>
          <dt>
            <Translate contentKey="attendanceApp.employee.manager">Manager</Translate>
          </dt>
          <dd>{employeeEntity.manager ? employeeEntity.manager.id : ''}</dd>
          <dt>
            <Translate contentKey="attendanceApp.employee.department">Department</Translate>
          </dt>
          <dd>{employeeEntity.department ? employeeEntity.department.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDetail;
