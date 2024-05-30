import {UUID} from "node:crypto";

export interface Project {
  projectId?: UUID;
  name?: string;
  description?: string;
  projectStatus?: string;
  startDate?: Date;
  finishDate?: Date;
}
