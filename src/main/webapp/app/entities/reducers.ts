import employee from 'app/entities/employee/employee.reducer';
import department from 'app/entities/department/department.reducer';
import attendance from 'app/entities/attendance/attendance.reducer';
import attendanceType from 'app/entities/attendance-type/attendance-type.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  employee,
  department,
  attendance,
  attendanceType,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
