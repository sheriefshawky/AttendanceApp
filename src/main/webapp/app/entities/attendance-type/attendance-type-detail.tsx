import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attendance-type.reducer';

export const AttendanceTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const attendanceTypeEntity = useAppSelector(state => state.attendanceType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attendanceTypeDetailsHeading">
          <Translate contentKey="attendanceApp.attendanceType.detail.title">AttendanceType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attendanceTypeEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="attendanceApp.attendanceType.code">Code</Translate>
            </span>
          </dt>
          <dd>{attendanceTypeEntity.code}</dd>
          <dt>
            <span id="descEn">
              <Translate contentKey="attendanceApp.attendanceType.descEn">Desc En</Translate>
            </span>
          </dt>
          <dd>{attendanceTypeEntity.descEn}</dd>
          <dt>
            <span id="descAr">
              <Translate contentKey="attendanceApp.attendanceType.descAr">Desc Ar</Translate>
            </span>
          </dt>
          <dd>{attendanceTypeEntity.descAr}</dd>
        </dl>
        <Button tag={Link} to="/attendance-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attendance-type/${attendanceTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttendanceTypeDetail;
