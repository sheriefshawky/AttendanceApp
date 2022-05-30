export interface IAttendanceType {
  id?: number;
  code?: string | null;
  descEn?: string | null;
  descAr?: string | null;
}

export const defaultValue: Readonly<IAttendanceType> = {};
