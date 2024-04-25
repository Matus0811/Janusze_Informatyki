import {UUID} from "node:crypto";

export interface Project {
  projectId: UUID;
  name: string;
  description: string;
  startDate: Date;
  finishDate: Date;
}
