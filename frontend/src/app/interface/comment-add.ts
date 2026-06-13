export interface CommentAdd {
  content: string | null | undefined;
  token: string | null;
  movieId: string | null;
  reviewAccountId: string;
  parentId: string | null;
}
