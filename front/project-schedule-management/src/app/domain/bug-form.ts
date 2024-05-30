import {UUID} from "node:crypto";

export interface BugForm {
  title?: string;
  description?: string;
  projectId?: string;
  taskCode?: string;
  username?: string;
  reportDate?: Date;
  bugType?: string;

}
