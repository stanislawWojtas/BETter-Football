import { getUrlFromPath } from './../shared/path';
import axios from 'axios';

export const getPresignedImageUrl = async (fileName: string): Promise<string> => {
  const response = await axios.get(getUrlFromPath(`api/files/presigned-url/${fileName}`));
  return response.data;
};