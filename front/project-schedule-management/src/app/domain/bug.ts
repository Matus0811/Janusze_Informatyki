import {Task} from "./task";

export interface Bug {
  serialNumber?: string,
  title?: string,
  description?: string,
  task?: Task,
  username?: string,
  bugType?: string,
  reportDate?: number,
  fixedDate?: number
}
