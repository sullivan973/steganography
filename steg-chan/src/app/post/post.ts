//Author: Thomas Sullivan
export class Post {

  constructor(
    public image?: string,
    public postId?: number,
    public threadId?: number,
    public message?: string,
    public title?: string
  ) {  }

}