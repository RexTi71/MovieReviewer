import { CommentResponse } from './comment-response';

interface Account{
  id:string;
  email:string;
  username:string;
}

export interface ReportResponse {
  account: Account;
  comment: CommentResponse;
}
