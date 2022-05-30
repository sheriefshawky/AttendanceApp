export interface IDepartment {
  id?: number;
  code?: string | null;
  nameEn?: string | null;
  nameAr?: string | null;
}

export const defaultValue: Readonly<IDepartment> = {};
