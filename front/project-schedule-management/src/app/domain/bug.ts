import {Task} from "./task";

export interface Bug {
  serialNumber?: string,
  title?: string,
  description?: string,
  task?: Task,
  username?: string,
  bugType?: string,
  bugStatus?: string,
  reportDate?: number,
  fixedDate?: number,
}
