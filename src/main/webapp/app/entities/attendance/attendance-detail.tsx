import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attendance.reducer';

export const AttendanceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const attendanceEntity = useAppSelector(state => state.attendance.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attendanceDetailsHeading">
          <Translate contentKey="attendanceApp.attendance.detail.title">Attendance</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attendanceEntity.id}</dd>
          <dt>
            <span id="logDate">
              <Translate contentKey="attendanceApp.attendance.logDate">Log Date</Translate>
            </span>
          </dt>
          <dd>{attendanceEntity.logDate ? <TextFormat value={attendanceEntity.logDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="logTimeFrom">
              <Translate contentKey="attendanceApp.attendance.logTimeFrom">Log Time From</Translate>
            </span>
          </dt>
          <dd>
            {attendanceEntity.logTimeFrom ? <TextFormat value={attendanceEntity.logTimeFrom} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="logTimeTo">
              <Translate contentKey="attendanceApp.attendance.logTimeTo">Log Time To</Translate>
            </span>
          </dt>
          <dd>
            {attendanceEntity.logTimeTo ? <TextFormat value={attendanceEntity.logTimeTo} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="permissionHours">
              <Translate contentKey="attendanceApp.attendance.permissionHours">Permission Hours</Translate>
            </span>
          </dt>
          <dd>{attendanceEntity.permissionHours}</dd>
          <dt>
            <span id="details">
              <Translate contentKey="attendanceApp.attendance.details">Details</Translate>
            </span>
          </dt>
          <dd>{attendanceEntity.details}</dd>
          <dt>
            <Translate contentKey="attendanceApp.attendance.attendanceType">Attendance Type</Translate>
          </dt>
          <dd>{attendanceEntity.attendanceType ? attendanceEntity.attendanceType.id : ''}</dd>
          <dt>
            <Translate contentKey="attendanceApp.attendance.employee">Employee</Translate>
          </dt>
          <dd>{attendanceEntity.employee ? attendanceEntity.employee.id : ''}</dd>
          <dt>
            <Translate contentKey="attendanceApp.attendance.managerApprovedBy">Manager Approved By</Translate>
          </dt>
          <dd>{attendanceEntity.managerApprovedBy ? attendanceEntity.managerApprovedBy.id : ''}</dd>
          <dt>
            <Translate contentKey="attendanceApp.attendance.hrApprovedBy">Hr Approved By</Translate>
          </dt>
          <dd>{attendanceEntity.hrApprovedBy ? attendanceEntity.hrApprovedBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/attendance" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attendance/${attendanceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttendanceDetail;
