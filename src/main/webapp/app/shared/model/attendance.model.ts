import dayjs from 'dayjs';
import { IAttendanceType } from 'app/shared/model/attendance-type.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IAttendance {
  id?: number;
  logDate?: string | null;
  logTimeFrom?: string | null;
  logTimeTo?: string | null;
  permissionHours?: number | null;
  details?: string | null;
  attendanceType?: IAttendanceType | null;
  employee?: IEmployee | null;
  managerApprovedBy?: IEmployee | null;
  hrApprovedBy?: IEmployee | null;
}

export const defaultValue: Readonly<IAttendance> = {};
