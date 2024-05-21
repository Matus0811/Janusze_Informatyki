import {UUID} from "node:crypto";

export interface Project {
  projectId?: UUID;
  name?: string;
  description?: string;
  projectStatus?: string;
  startDate?: number;
  finishDate?: number;
}
