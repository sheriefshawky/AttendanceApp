import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Employee from './employee';
import Department from './department';
import Attendance from './attendance';
import AttendanceType from './attendance-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}employee`} component={Employee} />
        <ErrorBoundaryRoute path={`${match.url}department`} component={Department} />
        <ErrorBoundaryRoute path={`${match.url}attendance`} component={Attendance} />
        <ErrorBoundaryRoute path={`${match.url}attendance-type`} component={AttendanceType} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
