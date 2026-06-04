import { Category } from './category';

export interface Movie {
  id: number;
  title: string;
  description: string;
  rating: string;
  productionDate: string;
  categories: Category[];
}
