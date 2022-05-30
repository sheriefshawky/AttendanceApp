import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AttendanceType from './attendance-type';
import AttendanceTypeDetail from './attendance-type-detail';
import AttendanceTypeUpdate from './attendance-type-update';
import AttendanceTypeDeleteDialog from './attendance-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AttendanceTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AttendanceTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AttendanceTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={AttendanceType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AttendanceTypeDeleteDialog} />
  </>
);

export default Routes;
